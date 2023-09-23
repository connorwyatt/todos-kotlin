import { delay, intersperse } from "rambdax"

const defaultTimeouts = [25, 50, 100, 200, 500, 1000]

const defaultPredicate = <T>(resource: T | null): boolean => {
    return resource != null
}

export const pollForResource = async <T>(
    fetcher: () => Promise<T | null>,
    predicate: (resource: T | null) => boolean = defaultPredicate,
): Promise<T | null> => {
    const timeouts = Array.from(defaultTimeouts, (t) => async () => await delay(t).then(() => null))
    const safeFetcher = async (): Promise<T | null> => await fetcher().catch(() => null)
    const fetchesAndTimeouts = [safeFetcher, ...intersperse(safeFetcher, timeouts), safeFetcher]

    for (const fetchOrTimeout of fetchesAndTimeouts) {
        const resource = await fetchOrTimeout()

        if (predicate(resource)) return resource
    }

    return null
}
