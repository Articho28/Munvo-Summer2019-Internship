package com.munvo.fileimport.hive.builder;

import com.munvo.fileimport.hive.importer.ImportInformation;

/**
 * 
 * @author Artsiom Skliar
 *
 */
public interface IHiveImporter {
	
	/**
	 * Generates HiveImporter object that will import data into hive table
	 * @return
	 */
	public ImportInformation generateImporter();
}
