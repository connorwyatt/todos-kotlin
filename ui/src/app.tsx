import { useTodos } from "~/todos/api/use-todos"

export const App = () => {
    const { data: todos } = useTodos()

    return <>{todos != null ? todos.map((todo) => <p key={todo.id}>{todo.title}</p>) : <p>Loading...</p>}</>
}
