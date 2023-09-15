// rentalDetails.js
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MultipleDatePicker from "react-multi-date-picker";

function RentalDetails() {

    const routeParams = useParams();
    const navigate = useNavigate();
    const [loading ,setLoading] = useState(true);
    const [rental, setRental] = useState([]);
    const [selectedDates, setSelectedDates] = useState([]);
    const rentalId = routeParams.id;

	useEffect(() => {
		const fetchOptions = {
			headers: {
				"Content-Type": "application/json",
				"Authorization": "Bearer "  + localStorage.getItem("jwt"),
			},
			method: "get"
		};
		fetch("https://localhost:8080/host/" + rentalId + "/info", fetchOptions)
        .then((response) => response.json())
        .then((response) => {
            setRental(response.Rental);
            setSelectedDates(response.Rental.availableDates);
            setLoading(false);
        })
        .catch((message) => {
            navigate("/unauthorized/user");
            return;
        });
	}, [rentalId,navigate]);

    if(loading){
        return(<h1>Loading...</h1>)
    }

    return (
        <>
            <h1>Rental Details :</h1>
            <div>
                <ul>
                <li key={rental.id}>
                    <h2>Basic : </h2>
                        <p>Title : {rental.title}</p>
                        <p>Charge per person : {rental.chargePerPerson}</p>
                        <p>Max guests : {rental.maxGuests}</p>
                        <p>Available dates : 
                            <MultipleDatePicker
                                value={selectedDates}
                                onSubmit={() => {}}
                            />
                        </p>

                    <h2>Space :</h2>
                        <p>Beds : {rental.beds}</p>
                        <p>Bedrooms : {rental.bedrooms}</p>
                        <p>Bathrooms : {rental.bathrooms}</p>
                        <p>Type : {rental.type === "publicRoom" && <>Public room</>} {rental.type === "privateRoom" && <>Private room</>} {rental.type === "house" && <>House</>}</p>
                        <p>Has Living Room : {rental.hasLivingRoom === true && <>True</>} {rental.hasLivingRoom === false && <>False</>}</p>
                        <p>Surface area : {rental.surfaceArea}</p>

                    <p>Description : {rental.description}</p>

                    <h2>Rules :</h2>
                        <p>Allow smoking : {rental.allowSmoking === true && <>True</>} {rental.allowSmoking === false && <>False</>}</p>
                        <p>Allow pets : {rental.allowPets === true && <>True</>} {rental.allowPets === false && <>False</>}</p>
                        <p>Allow events : {rental.allowEvents === true && <>True</>} {rental.allowEvents === false && <>False</>}</p>
                        <p>Min days : {rental.minDays}</p>

                    <h2>Location :</h2>
                        <p>City : {rental.address.city}</p>
                        <p>Neighbourhood : {rental.address.neighbourhood}</p>
                        <p>Street : {rental.address.street}</p>
                        <p>Floor No : {rental.address.floorNo}</p>
                    
                    <h2>Map :</h2>
                        <p>Public transport : {rental.publicTransport.map((item) => (<> {item} </>))}</p>

                    <h2>Photos :</h2>
                        <p>{rental.photos.map((item) => (<> {item} </>))}</p>

                    <h2>Amenities :</h2>
                        <p>Has WiFi : {rental.hasWiFi === true && <>True</>} {rental.hasWiFi === false && <>False</>}</p>
                        <p>Has AC : {rental.hasAC === true && <>True</>} {rental.hasAC === false && <>False</>}</p>
                        <p>Has heating : {rental.hasHeating === true && <>True</>} {rental.hasHeating === false && <>False</>}</p>
                        <p>Has kitchen : {rental.hasKitchen === true && <>True</>} {rental.hasKitchen === false && <>False</>}</p>
                        <p>Has TV : {rental.hasTV === true && <>True</>} {rental.hasTV === false && <>False</>}</p>
                        <p>Has parking : {rental.hasParking === true && <>True</>} {rental.hasParking === false && <>False</>}</p>
                        <p>Has elevator : {rental.hasElevator === true && <>True</>} {rental.hasElevator === false && <>False</>}</p>

                </li>
                </ul>
            </div>
            <a href="https://localhost:3000/host/rental/list">Rental List</a>
        </>
    )

}

export default RentalDetails;