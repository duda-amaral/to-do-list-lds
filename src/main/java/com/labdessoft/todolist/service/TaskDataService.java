package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.repository.TaskDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.labdessoft.todolist.enums.TaskTipo.DATA;

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
        obj.setType(DATA);

        obj = this.taskDataRepository.save(obj);

        String status = getStatus(obj.getId());
        obj.setStatus(status);
        return obj;
    }

    @Operation(description = "atualiza a descrição de uma tarefa do tipo data especificada pelo id")
    public TaskData update(TaskData obj)  {
        TaskData newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "completed", "type");
        String status = getStatus(obj.getId());
        newObj.setStatus(status);
        return this.taskDataRepository.save(newObj);
    }

    @Operation(description = "atualiza o status de uma tarefa do tipo data")
    public TaskData updateStatus(TaskData obj)  {
        TaskData newObj = findById(obj.getId());
        BeanUtils.copyProperties(obj, newObj, "id", "description", "dueDate", "status", "type");
        String status = getStatus(obj.getId());
        newObj.setStatus(status);
        return this.taskDataRepository.save(newObj);
    }

    @Operation(description = "deleta uma tarefa do tipo data especificada pelo id")
    public void delete(Long id) {
        TaskData task = findById(id);
        this.taskDataRepository.delete(task);

    }

    private void validateDate(TaskData task) {
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data prevista de execução deve ser igual ou superior à data atual.");
        }
    }

    @Operation(description = "Retorna o status de uma tarefa do tipo data especificada pelo id")
    public String getStatus(Long id) {
        TaskData task = findById(id);

        LocalDate dueDate = task.getDueDate();
        LocalDate currentDate = LocalDate.now();
        validateDate(task);

        if (task.getCompleted() == true) {
            return "Concluída";
        } else if (currentDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, currentDate);
            return daysLate + " dias de atraso";
        } else {
            return "Prevista: " + dueDate;
        }
    }
}
