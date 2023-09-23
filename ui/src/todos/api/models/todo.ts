export interface Todo {
    id: string
    title: string
    addedAt: Date
    isComplete: boolean
    completedAt: Date | null
}
