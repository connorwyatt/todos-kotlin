export type Subset<T> = {
    [attr in keyof T]?: T[attr] extends object
        ? Subset<T[attr]>
        : T[attr] extends object | null
        ? Subset<T[attr]> | null
        : T[attr] extends object | null | undefined
        ? Subset<T[attr]> | null | undefined
        : T[attr]
}
