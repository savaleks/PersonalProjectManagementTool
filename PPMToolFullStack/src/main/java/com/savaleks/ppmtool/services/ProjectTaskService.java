package com.savaleks.ppmtool.services;

import com.savaleks.ppmtool.domain.Backlog;
import com.savaleks.ppmtool.domain.ProjectTask;
import com.savaleks.ppmtool.repositories.BacklogRepository;
import com.savaleks.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        // add task to specific project
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getStatus()==""||projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        if (projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }
        return projectTaskRepository.save(projectTask);
    }
}
