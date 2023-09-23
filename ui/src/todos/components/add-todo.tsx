import { yupResolver } from "@hookform/resolvers/yup"
import { FC, useCallback } from "react"
import { useForm } from "react-hook-form"
import * as yup from "yup"

import { Panel } from "~/shared/components/layout/panel"
import { useAddTodoMutation } from "~/todos/api/use-add-todo-mutation"
import { Input } from "~/todos/components/add-todo.styles"

interface AddTodoFormValues {
    title: string
}

export const AddTodo: FC = () => {
    const {
        register,
        handleSubmit,
        formState: { isSubmitting },
        reset,
    } = useForm<AddTodoFormValues>({ resolver: yupResolver(validationSchema) })
    const { mutateAsync } = useAddTodoMutation()

    const submit = handleSubmit(
        useCallback(
            async ({ title }) => {
                await mutateAsync({ title })

                reset()
            },
            [mutateAsync, reset],
        ),
    )

    return (
        <div>
            <Panel removePadding>
                <form onSubmit={!isSubmitting ? submit : undefined}>
                    <Input {...register("title")} placeholder="Enter a todo" disabled={isSubmitting} autoFocus />
                </form>
            </Panel>
        </div>
    )
}

const validationSchema = yup.object({
    title: yup
        .string()
        .required(() => "Must not be empty.")
        .min(3, ({ min }) => `Must be at least ${min} characters.`)
        .max(100, ({ max }) => `Must be at most ${max} characters.`),
})
