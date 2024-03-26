package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Operation(description = "Pesquisa uma tarefa pelo id")
    public Task findById(Long id)  {
        return this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()));
    }

    @Operation(description = "lista todas as tarefas cadastradas")
    public List<Task> listAll() {
        return this.taskRepository.findAll();
    }
    @Operation(description = "cadastra tarefas")
    @Transactional
    public Task create(Task obj) {
        obj.setId(null);
        obj.setCompleted(false);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Operation(description = "atualiza uma tarefa especificada pelo id")
    public Task update(Task obj)  {
        Task newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "completed");
        return this.taskRepository.save(newObj);
    }

    @Operation(description = "atualiza o status de uma tarefa")
    public Task updateStatus(Task obj)  {
        Task newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "description");
        return this.taskRepository.save(newObj);
    }

    @Operation(description = "deleta uma tarefa especificada pelo id")
    public void delete(Long id) {
        Task task = findById(id);
        this.taskRepository.delete(task);
    }
}
