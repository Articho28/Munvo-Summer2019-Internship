package com.munvo.fileimport.bl.common;

import com.munvo.fileimport.bl.IBusinessLogic;

/**
 * 
 * @author Artsiom Skliar
 *
 */
public class BusinessLogicFactory {
	
	public static <BL extends IBusinessLogic> BL getBusinessLogic(Class<BL> businessLogicClass) {
		BL businessLogic = null;
		
		try {
			businessLogic = businessLogicClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		businessLogic.init();
		
		return businessLogic;
	}

}
