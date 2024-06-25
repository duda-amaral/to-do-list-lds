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
  