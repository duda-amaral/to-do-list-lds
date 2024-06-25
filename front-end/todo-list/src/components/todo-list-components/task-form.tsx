import React from "react";
import { Label } from "../ui/label";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "../ui/select";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "../ui/card";

const TaskForm = () => {
  return (
    <Card className="w-[350px] text-black bg-transparent">
      <CardHeader>
        <CardTitle>Criar tarefa</CardTitle>
        <CardDescription>Crie sua tarefa do tipo Livre.</CardDescription>
      </CardHeader>
      <CardContent>
        <form>
          <div className="grid w-full items-center gap-4">
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="description">Descrição</Label>
              <Input id="description" placeholder="descrição da tarefa" />
            </div>
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="prioridade">Prioridade</Label>
              <Select>
                <SelectTrigger id="prioridade">
                  <SelectValue placeholder="Select" />
                </SelectTrigger>
                <SelectContent position="popper">
                  <SelectItem value="ALTA">Alta</SelectItem>
                  <SelectItem value="MÉDIA">Média</SelectItem>
                  <SelectItem value="BAIXA">Baixa</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button variant="destructive">Cancel</Button>
        <Button variant="outline">Salvar</Button>
      </CardFooter>
    </Card>
  );
};

export default TaskForm;
