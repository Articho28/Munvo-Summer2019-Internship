package com.munvo.fileimport.bl.common;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.configuration.SpringContext;
import com.munvo.fileimport.hive.importer.ImportInformation;
import com.munvo.fileimport.hive.importer.ImporterThread;
import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;
import com.munvo.fileimport.request.service.IRequestService;

public class ImportBLTest {

	private IRequestService requestService;
	private ImportBL importBL;
	private List<Project> dummyProjects;
	
	@Before
	public void setUp() {
		
		//Mock dependencies
		requestService = mock(IRequestService.class);
		SpringContext springContext = mock(SpringContext.class);
		AppConfiguration appConfig = mock(AppConfiguration.class);
		importBL = mock(ImportBL.class);
		Whitebox.setInternalState(importBL, "appConfig", appConfig);
		Whitebox.setInternalState(importBL, "requestService", requestService);
		
		//Create Dummy Projects
		Project projectOne = new Project();
		projectOne.setId(1L);
		projectOne.setProjectCode("PR1");
		projectOne.setProjectName("ProjectOne");
		projectOne.setProjectType("FILE");
		projectOne.setFullQualifiedPath("/home/data/");
		
		Project projectTwo = new Project();
		projectTwo.setId(2L);
		projectTwo.setProjectCode("PR2");
		projectTwo.setProjectName("ProjectTwo");
		projectTwo.setProjectType("FILE");
		projectTwo.setFullQualifiedPath("home/data");
		
		Project projectThree = new Project();
		projectThree.setId(3L);
		projectThree.setProjectCode("PR3");
		projectThree.setProjectName("ProjectThree");
		projectThree.setProjectType("FILE");
		projectThree.setFullQualifiedPath("home/data");
		
		//Add to List for easy access
		dummyProjects = new ArrayList<>();
		dummyProjects.add(projectOne);
		dummyProjects.add(projectTwo);
		dummyProjects.add(projectThree);
		
		//key = mock(WatchKey.class);
		
		
	}
	
	@Test
	public void getImportTasks_OneImporterThread_OneEvent() throws IOException {
		
		Project projectOne = dummyProjects.get(0);
		Partition partitionOne = new Partition();
		partitionOne.setProject(projectOne);
		partitionOne.setPartitionName("ProjectOne_PR1_1234.csv");
		
		Job jobOne = new Job(projectOne.getProjectName());
		
		ImportInformation oneEventInfo = new ImportInformation();
		oneEventInfo.setJob(jobOne);
		oneEventInfo.setPartition(partitionOne);
		oneEventInfo.setProject(projectOne);
		
		
		ImporterThread expectedObject = new ImporterThread(oneEventInfo);
		
		//This is the final expected object by getImportTasks method
		List<ImporterThread> expectedList = new ArrayList<>();
		expectedList.add(expectedObject);
		
		
		//Create mocks necessary for method execution
		Path path = mock(Path.class);
		WatchEvent<Path> ev = mock(WatchEvent.class);
		when(ev.context()).thenReturn(path);
		when(path.toString()).thenReturn("ProjectOne_PR1_1234.csv");
		
		when(ev.kind()).thenReturn(StandardWatchEventKinds.ENTRY_CREATE);
		
		WatchKey watchKey = mock(WatchKey.class);
		List<WatchEvent<?>> events = new ArrayList<>();
		events.add(ev);
		when(watchKey.pollEvents()).thenReturn(events);
		
		List<Project> storedProjects = new ArrayList<>();
		storedProjects.add(projectOne);
		when(requestService.getAllProjects()).thenReturn(storedProjects);
		
		List<ImporterThread> actualOutput = importBL.getImportTasks(watchKey);
		
		
	}
	
	//TODO
	@Test 
	public void getImportTasks_ThreeImporterThreads_ThreeEvents() {
		assert(true);
	}
	
	@Test
	public void getImportTasks_EmptyListImporterThreads_NoEvents() {
		assert(true);
	}
	
	@SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
