package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.enums.TaskTipo;
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
        obj.setType(TaskTipo.LIVRE);
        obj.setStatus(getStatus(obj));
        return this.taskRepository.save(obj);
    }

    @Operation(description = "atualiza a descrição de uma tarefa especificada pelo id")
    public Task update(Task obj)  {
        Task newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "completed", "type");
        String status = getStatus(obj);
        newObj.setStatus(status);
        return this.taskRepository.save(newObj);
    }

    @Operation(description = "atualiza o status de uma tarefa")
    public Task updateStatus(Task obj)  {
        Task newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "description", "type");
        String status = getStatus(obj);
        newObj.setStatus(status);
        return this.taskRepository.save(newObj);
    }

    @Operation(description = "deleta uma tarefa especificada pelo id")
    public void delete(Long id) {
        Task task = findById(id);
        this.taskRepository.delete(task);
    }

    @Operation(description = "Retorna o status de uma tarefa especificada pelo id")
    public String getStatus(Task task) {

        if (task.getCompleted() == true) {
            return "Concluída";
        }
      return "Pendente";
    }
}
