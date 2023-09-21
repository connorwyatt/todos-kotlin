import { formatDistanceToNowStrict } from "date-fns"
import { FC, useState } from "react"
import { useHarmonicIntervalFn } from "react-use"

export interface DistanceToNowProps {
    date: Date
}

const format = (date: Date) => formatDistanceToNowStrict(date, { addSuffix: true })

export const DistanceToNow: FC<DistanceToNowProps> = ({ date }) => {
    const [formatted, setFormatted] = useState(format(date))

    useHarmonicIntervalFn(() => {
        setFormatted(format(date))
    }, 1000)

    return formatted
}
