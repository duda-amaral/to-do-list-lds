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
    <div className="p-4 sm:p-6 md:p-8 lg:p-10">
      <div className="overflow-x-auto">
        <table className="w-full table-auto border-collapse rounded-lg shadow-lg">
          <thead>
            <tr className="bg-gray-200">
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Description
              </th>
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Completed
              </th>
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Priority
              </th>
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Type
              </th>
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Status
              </th>
              <th className="px-6 py-3 text-left font-medium text-gray-700">
                Actions
              </th>
            </tr>
          </thead>
          <tbody>
            {tasks.map((task) => (
              <tr
                key={task.id}
                className={`border-b ${
                  task.status === "Completed"
                    ? "bg-green-100"
                    : task.status === "Em andamento"
                    ? "bg-yellow-100"
                    : "bg-white"
                } hover:bg-gray-100 transition-colors`}
              >
                <td className="px-6 py-4">
                  {editingTaskId === task.id ? (
                    <Input
                      type="text"
                      value={newDescription}
                      onChange={(e) => setNewDescription(e.target.value)}
                      className="w-full"
                    />
                  ) : (
                    task.description
                  )}
                </td>
                <td className="px-6 py-4">
                  {task.completed ? (
                    "Sim"
                  ) : (
                    "Não"
                  )}
                </td>
                <td className="px-6 py-4">
                  {editingTaskId === task.id ? (
                    <Select
                      value={newPriority}
                      onValueChange={(value) =>
                        setNewPriority(value as Prioridade)
                      }
                    >
                      <SelectTrigger id="priority">
                        <SelectValue placeholder="Select" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="BAIXA">Baixa</SelectItem>
                        <SelectItem value="MÉDIA">Média</SelectItem>
                        <SelectItem value="ALTA">Alta</SelectItem>
                      </SelectContent>
                    </Select>
                  ) : (
                    task.priority
                  )}
                </td>
                <td className="px-6 py-4">
                  {task.type}
                </td>
                <td className="px-6 py-4">
                  {task.status}
                </td>
                <td className="px-6 py-4 flex items-center gap-2">
                  {editingTaskId === task.id ? (
                    <>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleSave(task.id, tasks, newDescription, newPriority, newType)}
                      >
                        Save
                      </Button>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => setEditingTaskId(null)}
                      >
                        Cancel
                      </Button>
                    </>
                  ) : (
                    <>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleEdit(task.id)}
                      >
                        Editar
                      </Button>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleDelete(task.id, setTasks)}
                      >
                        Deletar
                      </Button>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleComplete(task.id, task.completed, setTasks)}
                      >
                        Completada
                      </Button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default TasksTable;
