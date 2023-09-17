import { JsonViewer } from "~/app.styles"
import { useTodos } from "~/todos/api/use-todos"

export const App = () => {
    const { data: todos } = useTodos()

    return <JsonViewer>{JSON.stringify(todos, null, 4)}</JsonViewer>
}
