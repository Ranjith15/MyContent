package com.edassist.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.edassist.service.HotConfigurationService;
import com.edassist.utils.CommonUtil;

@Component("hotConfigurationService")
public class HotConfigurationServiceImpl implements HotConfigurationService {

	@Override
	@PostConstruct
	public void reloadHotConfiguration() throws Exception {
		CommonUtil.loadHotProperties();
		;
	}
}
