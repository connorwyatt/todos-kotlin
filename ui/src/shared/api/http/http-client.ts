import axios from "axios"
import { stringify } from "qs"

export const httpClient = axios.create()

httpClient.defaults.timeout = 30000

httpClient.defaults.paramsSerializer = (params) => stringify(params, { arrayFormat: "brackets", skipNulls: true })
