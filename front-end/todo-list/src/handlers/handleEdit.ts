import { useState } from 'react';
import { Task, Prioridade, TaskTipo } from '@/types';

export const useHandleEdit = (tasks: Task[]) => {
  const [editingTaskId, setEditingTaskId] = useState<number | null>(null);
  const [newDescription, setNewDescription] = useState("");
  const [newCompleted, setNewCompleted] = useState(false);
  const [newPriority, setNewPriority] = useState<Prioridade>(Prioridade.ALTA);
  const [newType, setNewType] = useState<TaskTipo>(TaskTipo.DATA);
  const [newStatus, setNewStatus] = useState("");

  const handleEdit = (taskId: number) => {
    setEditingTaskId(taskId);
    const task = tasks.find((t) => t.id === taskId);
    if (task) {
      setNewDescription(task.description);
      setNewCompleted(task.completed);
      setNewPriority(task.priority);
      setNewType(task.type);
      setNewStatus(task.status);
    }
  };

  return {
    editingTaskId,
    newDescription,
    newCompleted,
    newPriority,
    newType,
    newStatus,
    setEditingTaskId,
    setNewDescription,
    setNewCompleted,
    setNewPriority,
    setNewType,
    setNewStatus,
    handleEdit,
  };
};