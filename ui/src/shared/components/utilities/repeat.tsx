import { FC, Fragment, PropsWithChildren } from "react"

export interface RepeatProps extends PropsWithChildren {
    times: number
}

export const Repeat: FC<RepeatProps> = ({ children, times }) => {
    return (
        <>
            {[...Array(times)].map((_, index) => (
                // eslint-disable-next-line react/no-array-index-key
                <Fragment key={index}>{children}</Fragment>
            ))}
        </>
    )
}
