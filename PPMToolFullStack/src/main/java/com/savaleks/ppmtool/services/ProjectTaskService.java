package com.savaleks.ppmtool.services;

import com.savaleks.ppmtool.domain.Backlog;
import com.savaleks.ppmtool.domain.Project;
import com.savaleks.ppmtool.domain.ProjectTask;
import com.savaleks.ppmtool.exceptions.ProjectNotFoundException;
import com.savaleks.ppmtool.repositories.BacklogRepository;
import com.savaleks.ppmtool.repositories.ProjectRepository;
import com.savaleks.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {
            // add task to specific project
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e){
            throw new ProjectNotFoundException("Project not found.");
        }
    }

   public Iterable<ProjectTask> findBacklogById(String id){
       Project project = projectRepository.findByProjectIdentifier(id);
       if (project == null){
           throw new ProjectNotFoundException("Project with id: " + id + " does not exist.");
       }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
   }
}
