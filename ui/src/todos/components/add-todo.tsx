import { yupResolver } from "@hookform/resolvers/yup"
import { FC, useCallback } from "react"
import { useForm } from "react-hook-form"
import { useMeasure } from "react-use"
import * as yup from "yup"

import { Icon } from "~/shared/components/icons/icons"
import { Panel } from "~/shared/components/layout/panel"
import { useAddTodoMutation } from "~/todos/api/use-add-todo-mutation"
import { Button, Form, Input } from "~/todos/components/add-todo.styles"

interface AddTodoFormValues {
    title: string
}

export const AddTodo: FC = () => {
    const [ref, { height }] = useMeasure<HTMLFormElement>()

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
                <Form onSubmit={!isSubmitting ? submit : undefined} ref={ref}>
                    <Input {...register("title")} placeholder="Enter a todo" disabled={isSubmitting} autoFocus />

                    <Button type="submit" $size={height}>
                        <Icon name="arrowForward" />
                    </Button>
                </Form>
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
