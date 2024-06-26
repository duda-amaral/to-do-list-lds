export enum Prioridade {
    ALTA = "ALTA",
    MÉDIA = "MÉDIA",
    BAIXA = "BAIXA",
  }
  
  export enum TaskTipo {
    DATA = "DATA",
    LIVRE = "LIVRE",
    PRAZO = "PRAZO",
  }
  
  export interface Task {
    id: number;
    description: string;
    completed: boolean;
    priority: Prioridade;
    type: TaskTipo;
    status: string;
  }

  export interface TaskPrazo extends Task{
    dueDays: number;
  }

  export interface TaskData extends Task{
    dueDate: string;
  }
  