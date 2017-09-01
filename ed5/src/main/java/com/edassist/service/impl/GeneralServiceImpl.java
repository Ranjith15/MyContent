package com.edassist.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edassist.constants.MongoConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.General;
import com.edassist.mongodb.repository.GeneralRepository;
import com.edassist.service.GeneralService;
import com.edassist.service.PropagationService;
import org.springframework.util.StringUtils;

@Service
public class GeneralServiceImpl implements GeneralService {

	@Autowired
	private GeneralRepository generalRepository;

	@Autowired
	private PropagationService propagationService;

	@Override
	public List<General> findByComponent(String component, String client, String program, boolean textOnly, Date signedDate) throws BadRequestException {
		List<General> contentList = new ArrayList<>();

		if (!client.equals(MongoConstants.CLIENT_GLOBAL)) {
			String programRegex = getProgramIds(program);
			contentList.addAll(generalRepository.findByComponent(component, client, programRegex, signedDate));
			List<General> globalContent = generalRepository.findByComponent(component, MongoConstants.CLIENT_GLOBAL, MongoConstants.PROGRAM_GLOBAL, signedDate);
			contentList = this.mergeClientAndGlobalContent(globalContent, contentList);
			if (!StringUtils.isEmpty(programRegex) && !programRegex.equals("clientDefault")) {
				contentList = removeDuplicateContent(contentList);
			}
		} else {
			contentList = generalRepository.findByComponent(component, MongoConstants.CLIENT_GLOBAL, MongoConstants.PROGRAM_GLOBAL, signedDate);
		}
		contentList = parseHTMLContent(contentList, textOnly);

		Collections.sort(contentList);

		return contentList;
	}

	@Override
	public List<General> findByName(String component, String name, String client, String program, boolean textOnly, boolean cascade) throws BadRequestException {
		List<General> contentList = new ArrayList<>();

		if (!client.equals(MongoConstants.CLIENT_GLOBAL)) {
			if (cascade) {
				String programRegex = getProgramIds(program);
				contentList.addAll(generalRepository.findByName(component, name, client, programRegex));
				List<General> globalContent = generalRepository.findByName(component, name, MongoConstants.CLIENT_GLOBAL, MongoConstants.PROGRAM_GLOBAL);
				contentList = this.mergeClientAndGlobalContent(globalContent, contentList);
				if (!StringUtils.isEmpty(programRegex) && !programRegex.equals("clientDefault")) {
					contentList = removeDuplicateContent(contentList);
				}
			} else {
				contentList = generalRepository.findByName(component, name, client, program);
			}
		} else {
			contentList = generalRepository.findByName(component, name, MongoConstants.CLIENT_GLOBAL, MongoConstants.PROGRAM_GLOBAL);
		}

		contentList = parseHTMLContent(contentList, textOnly);

		Collections.sort(contentList);

		return contentList;
	}

	@Override
	public List<General> findByClient(String client, boolean textOnly) throws BadRequestException {
		List<General> contentList = new ArrayList<>();

		contentList.addAll(generalRepository.findByClient(client));

		if (!client.equals(MongoConstants.CLIENT_GLOBAL)) {
			List<General> globalContent = generalRepository.findByClient(MongoConstants.CLIENT_GLOBAL);
			contentList = this.mergeClientAndGlobalContent(globalContent, contentList);
		}

		contentList = parseHTMLContent(contentList, textOnly);

		return contentList;
	}

	@Override
	public List<General> findByProgram(String client, String program, boolean textOnly) {
		List<General> contentList = generalRepository.findByProgram(client, program);

		contentList = parseHTMLContent(contentList, textOnly);

		return contentList;
	}

	private List<General> parseHTMLContent(List<General> contentList, boolean textOnly) {
		for (General content : contentList) {
			if (textOnly) {
				content.setData(Jsoup.clean(content.getData(), Whitelist.none()));
			} else {
				content.setData(Jsoup.clean(content.getData(), Whitelist.relaxed().addAttributes("a", "target")));
			}
		}
		return contentList;
	}

	private String getProgramIds(String programs) {
		if (StringUtils.isEmpty(programs)) {
			return programs;
		} else if (programs.indexOf("clientDefault") < 0) {
			programs = programs + ",clientDefault";
			return programs.replace(",", "|");
		} else {
			return programs.replace(",", "|");
		}
	}

	private List<General> mergeClientAndGlobalContent(List<General> globalContent, List<General> clientContent) {
		List<General> contentList = new ArrayList<>();
		if (clientContent != null && !clientContent.isEmpty()) {
			contentList = new ArrayList<>(clientContent);
			if (globalContent != null && globalContent.size() > 0) {
				globalContent.removeIf(content -> clientContent.stream()
						.map(General::getName)
						.collect(Collectors.toList())
						.contains(content.getName()));
				contentList.addAll(globalContent);
			}
		} else if (globalContent != null && !globalContent.isEmpty()) {
			contentList.addAll(globalContent);
		}
		return contentList;
	}

	//Remove client default content if program specific content exists.
	private List<General> removeDuplicateContent(List<General> contentList) {
		if (contentList != null && !contentList.isEmpty()) {
			List<General> programSpecificContent = contentList.stream()
					.filter(s -> !s.getProgram().equals("clientDefault"))
					.collect(Collectors.toList());
			if (programSpecificContent != null && !programSpecificContent.isEmpty()) {
				List<General> defaultContent = contentList.stream()
						.filter(s -> s.getProgram().equals("clientDefault"))
						.collect(Collectors.toList());
				if (defaultContent != null && !defaultContent.isEmpty()) {
					defaultContent.removeIf(content -> programSpecificContent.stream()
							.map(General::getName)
							.collect(Collectors.toList())
							.contains(content.getName()));
					defaultContent.addAll(programSpecificContent);
					return defaultContent;
				}
			} else {
				return contentList;
			}
		}
		return contentList;
	}

	@Override
	public General saveOrUpdate(General content) {
		General insertedContent = new General();
		// if we are updating an existing content record, preserve createdBy and createdAt values
		if (content.getId() != null) {
			General oldContent = generalRepository.findOne(content.getId());
			if (oldContent != null) {
				if (!content.getIsDeleted()) {
					content.setCreatedAt(oldContent.getCreatedAt());
					content.setCreatedBy(oldContent.getCreatedBy());
					content.setModifiedAt(new Date());
					content.setNeedsPropagation(true);
				}
			}
			insertedContent = generalRepository.save(content);
		} else {
			if (content.getData() == null) {
				content.setData("");
			}
			content.setCreatedAt(new Date());
			content.setNeedsPropagation(true);
			insertedContent = generalRepository.save(content);
		}
		return insertedContent;
	}

	@Override
	public void deleteById(String id) {
		General generalContent = generalRepository.findOne(id);
		if (generalContent != null) {
			propagationService.deleteGeneralContent(generalContent);
		}
		generalRepository.delete(id);
	}

	@Override
	public List<String> findUniqueComponents() {
		return generalRepository.findUniqueComponents();
	}

	@Override
	public List<String> findUniqueNamesForComponent(String component) {
		return generalRepository.findUniqueNamesForComponent(component);
	}

	@Override
	public List<String> findCollectionNames() {
		return generalRepository.findCollectionNames();
	}
}
