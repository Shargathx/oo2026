export type Discipline =
  | "HUNDRED_M"
  | "LONG_JUMP";

export type Result = {
    id?: number
    discipline: Discipline
    score: number
    value: number
    competitorId: number
}