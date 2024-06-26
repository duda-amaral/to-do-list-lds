"use client";
import React, { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectItem,
  SelectContent,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { fetchTasks } from "@/api/fetchTasks";
import { useHandleEdit } from "@/handlers/handleEdit";
import { handleSave } from "@/handlers/handleSave";
import { handleDelete } from "@/handlers/handleDelete";
import { handleComplete } from "@/handlers/handleComplete";
import { Task, Prioridade } from "@/types";

const TasksTable = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const {
    editingTaskId,
    newDescription,
    newPriority,
    newType,
    setEditingTaskId,
    setNewDescription,
    setNewPriority,
    handleEdit,
  } = useHandleEdit(tasks);

  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchTasks();
      setTasks(data);
    };

    fetchData();
  }, []);

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 p-4 sm:p-6 md:p-8 lg:p-10">
      {tasks.map((task) => (
        <div
          key={task.id}
          className={`bg-white rounded-lg shadow-md p-4 flex flex-col justify-between ${task.status === 'Completed' ? 'bg-green-100' : task.status === 'Em andamento' ? 'bg-yellow-100' : 'bg-white'} hover:bg-gray-100 transition-colors`}
        >
          <div>
            <div className="flex justify-between items-center mb-2">
              <h3 className="text-lg font-medium">{task.description}</h3>
              <span className={`px-2 py-1 text-xs font-semibold rounded ${task.completed ? 'bg-green-200 text-green-800' : 'bg-gray-200 text-gray-800'}`}>
                {task.completed ? 'Completada' : 'Não completada'}
              </span>
            </div>
            <p className="text-sm text-gray-700 mb-2">Prioridade: {task.priority}</p>
            <p className="text-sm text-gray-700 mb-2">Tipo: {task.type}</p>
            <p className="text-sm text-gray-700 mb-2">Status: {task.status}</p>
          </div>
          <div className="flex justify-end gap-2 mt-2">
            {editingTaskId === task.id ? (
              <>
                <Input
                  type="text"
                  value={newDescription}
                  onChange={(e) => setNewDescription(e.target.value)}
                  className="w-full mb-2"
                />
                <Select
                  value={newPriority}
                  onValueChange={(value) => setNewPriority(value as Prioridade)}
                >
                  <SelectTrigger id="priority">
                    <SelectValue>{newPriority}</SelectValue>
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="BAIXA">Baixa</SelectItem>
                    <SelectItem value="MÉDIA">Média</SelectItem>
                    <SelectItem value="ALTA">Alta</SelectItem>
                  </SelectContent>
                </Select>
                <Button
                  size="sm"
                  onClick={() => handleSave(task.id, tasks, newDescription, newPriority, newType)}
                  className="mt-2"
                >
                  Save
                </Button>
                <Button
                  size="sm"
                  variant="outline"
                  onClick={() => setEditingTaskId(null)}
                  className="mt-2"
                >
                  Cancel
                </Button>
              </>
            ) : (
              <>
                <Button
                  size="sm"
                  onClick={() => handleEdit(task.id)}
                  className="mt-2"
                >
                  Editar
                </Button>
                <Button
                  size="sm"
                  variant="outline"
                  onClick={() => handleDelete(task.id, setTasks)}
                  className="mt-2"
                >
                  Deletar
                </Button>
                <Button
                  size="sm"
                  onClick={() => handleComplete(task.id, task.completed, setTasks)}
                  className="mt-2"
                >
                  Completada
                </Button>
              </>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default TasksTable;
