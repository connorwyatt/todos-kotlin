export type FontStyle = "normal" | "italic" | "oblique"

export type FontWeight = 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900

export const weightToName = (weight: FontWeight) => {
    switch (weight) {
        case 100:
            return "thin"
        case 200:
            return "extra-light"
        case 300:
            return "light"
        case 400:
            return "regular"
        case 500:
            return "medium"
        case 600:
            return "semibold"
        case 700:
            return "bold"
        case 800:
            return "extra-bold"
        case 900:
            return "black"
    }
}
