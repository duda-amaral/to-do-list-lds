const API_BASE_URL = "https://to-do-list-lds.onrender.com";

export const fetchTasks = async () => {
  console.log("Fetching tasks...");
  try {
    const response = await fetch(`${API_BASE_URL}/api/task`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch tasks, status: ${response.status}`);
    }

    const data = await response.json();
    console.log("Resposta da API:", data);

    if (!data || data.length === 0) {
      console.error("Empty or malformed JSON data received:", data);
      return [];
    }

    return data;
  } catch (error) {
    console.error("Error fetching tasks:", error);
    return [];
  }
};

export const updateTask = async (taskId: number, updatedTask: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/task/${taskId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedTask),
    });

    if (!response.ok) {
        fetchTasks();
      throw new Error("Failed to update task");
    }
  } catch (error) {
    console.error("Error updating task:", error);
  }
};

export const deleteTask = async (taskId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/task/${taskId}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("Failed to delete task");
    }
  } catch (error) {
    console.error("Error deleting task:", error);
  }
};

export const completeTask = async (taskId: number, completed: boolean) => {
  try {
    const response = await fetch(
      `${API_BASE_URL}/api/task/${taskId}/completed`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ completed }),
      },
    );

    if (!response.ok) {
      throw new Error("Failed to complete task");
    }
  } catch (error) {
    console.error("Error completing task:", error);
  }
};

export const createTask = async (description: string, priority: string) => {
  const response = await fetch("https://to-do-list-lds.onrender.com/api/task", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ description, priority }),
  });
  if (!response.ok) {
    throw new Error("Erro ao criar tarefa");
  }
  fetchTasks();
};

export const createTaskData = async (description: string, priority: string, dueDate: Date | null) => {
  const response = await fetch("https://to-do-list-lds.onrender.com/api/taskdata", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ description, priority, dueDate }),
  });
  if (!response.ok) {
    throw new Error("Erro ao criar tarefa");
  }
  fetchTasks();
};

export const createTaskPrazo = async (description: string, priority: string, dueDays: number | null) => {
  const response = await fetch("https://to-do-list-lds.onrender.com/api/taskprazo", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ description, priority, dueDays }),
  });
  if (!response.ok) {
    throw new Error("Erro ao criar tarefa");
  }
  fetchTasks();
};