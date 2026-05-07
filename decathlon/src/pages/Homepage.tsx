import { useEffect, useState } from "react";
import type { Result, Discipline } from "../models/Result";
import type { Competitor } from "../models/Competitor";

function ResultsPage() {
    const [results, setResults] = useState<Result[]>([]);
    const [competitors, setCompetitors] = useState<Competitor[]>([]);

    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(5);

    const [sort, setSort] = useState("score,desc");
    const [country, setCountry] = useState("");

    // form state
    const [discipline, setDiscipline] = useState<Discipline>("HUNDRED_M");
    const [score, setScore] = useState("");
    const [value, setValue] = useState("");
    const [competitorId, setCompetitorId] = useState<number | "">("");

    const baseUrl = import.meta.env.VITE_BACK_URL;

    useEffect(() => {
        fetch(
            baseUrl +
            `/results?page=${page}&size=${size}&sort=${sort}` +
            (country ? `&country=${country}` : "")
        )
            .then(res => res.json())
            .then(json => {
                setResults(json.content);
                setTotalPages(json.totalPages);
                setTotalElements(json.totalElements);
            });
    }, [page, size, sort, country]);

    useEffect(() => {
        fetch(baseUrl + "/athletes")
            .then(res => res.json())
            .then(setCompetitors);
    }, []);

    const addResult = () => {
        if (!discipline || !score || !value || !competitorId) {
            return alert("Fill all fields!");
        }

        fetch(baseUrl + `/athletes/${competitorId}/results`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                discipline,
                score: Number(score),
                value: Number(value),
            })
        })
            .then(res => res.json())
            .then(() => {
                setPage(0);

                // reset form after adding results
                setScore("");
                setValue("");
                setCompetitorId("");
                setDiscipline("HUNDRED_M");
            });
    };

    const changeSize = (newSize: number) => {
        setSize(newSize);
        setPage(0);
    };

    const changeSort = (newSort: string) => {
        setSort(newSort);
        setPage(0);
    };

    const changeCountry = (newCountry: string) => {
        setCountry(newCountry);
        setPage(0);
    };

    return (
        <div>
            <div style={{ marginBottom: "20px" }}>
                <h3>Add Result</h3>

                <select
                    value={discipline}
                    onChange={(e) => setDiscipline(e.target.value as Discipline)}
                >
                    <option value="HUNDRED_M">100m</option>
                    <option value="LONG_JUMP">Long Jump</option>
                </select>

                <input
                    placeholder="Score"
                    value={score}
                    onChange={(e) => setScore(e.target.value)}
                />

                <input
                    placeholder="Value"
                    value={value}
                    onChange={(e) => setValue(e.target.value)}
                />

                <select
                    value={competitorId}
                    onChange={(e) => setCompetitorId(Number(e.target.value))}
                >
                    <option value="">Select competitor</option>
                    {competitors.map(c => (
                        <option key={c.id} value={c.id}>
                            {c.name} ({c.country})
                        </option>
                    ))}
                </select>

                <button onClick={addResult}>Add Result</button>
            </div>

            <div>
                Showing {page * size + 1} -{" "}
                {(page + 1) * size > totalElements
                    ? totalElements
                    : (page + 1) * size}
                {" "}of {totalElements}
            </div>

            <select value={size} onChange={(e) => changeSize(Number(e.target.value))}>
                <option value="1">1</option>
                <option value="3">3</option>
                <option value="5">5</option>
            </select>

            <button onClick={() => changeSort("score,desc")}>Best score</button>
            <button onClick={() => changeSort("score,asc")}>Worst score</button>
            <button onClick={() => changeSort("value,asc")}>Value ↑</button>
            <button onClick={() => changeSort("value,desc")}>Value ↓</button>
            <button onClick={() => changeSort("discipline,asc")}>Discipline A-Z</button>

            <select value={country} onChange={(e) => changeCountry(e.target.value)}>
                <option value="">All countries</option>
                <option value="EST">Estonia</option>
                <option value="USA">USA</option>
                <option value="FIN">Finland</option>
            </select>

            {results.map(result => (
                <div key={result.id}>
                    <strong>{result.discipline}</strong> |
                    Score: {result.score} |
                    Value: {result.value} |
                    Competitor: {result.competitorId}
                </div>
            ))}

            <button disabled={page === 0} onClick={() => setPage(page - 1)}>
                Previous
            </button>

            <span>{page + 1} / {totalPages}</span>

            <button disabled={page + 1 >= totalPages} onClick={() => setPage(page + 1)}>
                Next
            </button>

        </div>
    );
}

export default ResultsPage;