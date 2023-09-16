// listRentals.js
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function ListRentals() {

    const navigate = useNavigate();
	const [loading, setLoading] = useState(true);
    const [rentals, setRentals] = useState([]);
    const [maxPage, setMaxPage] = useState();
    const [pageNum, setPageNum] = useState(0);
    const [pageSize, setPageSize] = useState(2);

	useEffect(() => {
        const reqBody = {
            "pageNo" : pageNum,
            "pageSize" : pageSize,
            "sort" : "ASC",
            "sortByColumn" : "id"
        };
		const fetchOptions = {
			headers: {
				"Content-Type": "application/json",
				"Authorization": "Bearer "  + localStorage.getItem("jwt"),
			},
			method: "post",
            body: JSON.stringify(reqBody)
		};
		fetch("https://localhost:8080/host/rental/list", fetchOptions)
        .then((response) => response.json())
        .then((response) => {
            setRentals(response.Rentals.content);
            setMaxPage(response.Rentals.totalPages);
            setLoading(false);
        })
        .catch((message) => {
            console.log(message);
            navigate("/unauthorized/user");
            return;
        });
	}, [pageNum, pageSize, navigate]);

    const nextPage = () => {
        if (pageNum + 1 < maxPage) setPageNum(pageNum + 1);
    };

    const previousPage = () => {
        if (pageNum > 0) setPageNum(pageNum - 1);
    };

    const firstPage = () => {
        setPageNum(0);
    };

    const lastPage = () => {
        setPageNum(maxPage - 1);
    };

    const changePageSize = (newPageSize) => {
        setPageSize(newPageSize);
    };

	if (loading === true){
		return (<h1>Loading...</h1>);
	}

	return (
		<>
			<h1> Rental list : </h1>
            <div>
                <ul>
                    {rentals.map((data) => (
                    <li key={data.id}>
                        <p><a href={"https://localhost:3000/host/rental/" + data.id + "/details"}>{data.title}</a></p>
                    </li>
                ))}
                </ul>
            </div>
            <div>
                Current page : {pageNum + 1}
            </div>
            <div>
                <button id="submit" type="button" onClick={() => nextPage()}>
                        Next Page
                </button>
            </div>
            <div>
                <button id="submit" type="button" onClick={() => previousPage()}>
                        Previous Page
                </button>
            </div>
            <div>
                {pageNum > 0 && 
                    <>
                        <button id="submit" type="button" onClick={() => firstPage()}>
                                First Page
                        </button>
                    </>
                }
            </div>
            <div>
                {pageNum < maxPage - 1 && 
                    <>
                        <button id="submit" type="button" onClick={() => lastPage()}>
                                Last Page
                        </button>
                    </>
                }
            </div>
            <div>
                <label for="numPages" >
					Rentals per page : 
                </label>
                <select name="numPages" id="pageSize" value={pageSize} onChange={(event) => changePageSize(event.target.value)}>
                    <option value= {1}> 1 </option>
                    <option value={2}> 2 </option>
                    <option value={5}> 5 </option>
                    <option value={10}> 10 </option>
                </select>
            </div>
            <div> <a href = 'https://localhost:3000/host/hostHome'>Homepage</a> </div>
		</>
	);
}

export default ListRentals;