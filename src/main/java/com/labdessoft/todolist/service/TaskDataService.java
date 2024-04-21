package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.repository.TaskDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskDataService {

    @Autowired
    private TaskDataRepository taskDataRepository;

    @Operation(description = "Pesquisa uma tarefa do tipo data pelo id")
    public TaskData findById(Long id)  {
        return this.taskDataRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + TaskData.class.getName()));
    }

    @Operation(description = "lista todas as tarefas do tipo data cadastradas")
    public List<TaskData> listAll() {
        return this.taskDataRepository.findAll();
    }
    @Operation(description = "cadastra tarefas")
    @Transactional
    public TaskData create(TaskData obj) {
        obj.setId(null);
        obj.setCompleted(false);
        obj = this.taskDataRepository.save(obj);
        return obj;
    }

    @Operation(description = "atualiza a descrição de uma tarefa do tipo data especificada pelo id")
    public TaskData update(TaskData obj)  {
        TaskData newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "completed");
        return this.taskDataRepository.save(newObj);
    }

    @Operation(description = "atualiza o status de uma tarefa do tipo data")
    public TaskData updateStatus(TaskData obj)  {
        TaskData newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "description");
        return this.taskDataRepository.save(newObj);
    }

    @Operation(description = "deleta uma tarefa do tipo data especificada pelo id")
    public void delete(Long id) {
        TaskData task = findById(id);
        this.taskDataRepository.delete(task);

    }

    private void validateTask(TaskData task) {
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data prevista de execução deve ser igual ou superior à data atual.");
        }
    }
}
