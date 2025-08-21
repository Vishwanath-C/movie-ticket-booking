import apiClient from "../src/api";
import { useState } from "react";
import { Button, Container, Dropdown, DropdownButton } from "react-bootstrap";

const GenerateSeatLayout = () => {
    const [theatres, setTheatres] = useState([]);
    const token = localStorage.getItem('token');
    const [selectedTheatre, setSelectedTheatre] = useState(null);
    const [seatTypes, setSeatTypes] = useState([]);
    const [selectedSeatType, setSelectedSeatType] = useState(null);

    const [price, setPrice] = useState('');
    // const [numberOfSeats, setNumberOfSeats] = useState('');
    const [rowCount, setRowCount] = useState('');
    const [seatsPerRow, setSeatsPerRow] = useState('');

    const fetchTheatres = async () => {
        try {
            console.log("In fetch theatres");
            const response = await apiClient.get("/theatres/get-all-theatres", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setTheatres(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error fetching theatres:", error);
        }
    };

    const fetchSeatTypes = async () => {
        try {
            console.log("In seat types");
            const response = await apiClient.get('/seats/get-seat-types', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            console.log(response);
            setSeatTypes(response.data);

        } catch (error) {

        }

    };

    const handleSubmit = async (e) => {
        try {
            const response = await apiClient.post(`/seats/generate_seats/theatres/${selectedTheatre.id}`,
                 [{seatType: selectedSeatType, price, rowCount, seatsPerRow}] , {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            setSelectedTheatre(null);
            setSelectedSeatType(null);
            setPrice('');
            setRowCount('');
            setSeatsPerRow('');

        } catch (error) {

        }
    };


    return (
        <Container className="mt-4 border border-dark rounded bg-light p-4 mb-4 w-50">
            <h3 className="text-center fw-bold">Generate Seats</h3>

            <Button onClick={fetchTheatres} className="mb-3 d-block mx-auto">
                Load Theatres
            </Button>

            <form onSubmit={handleSubmit}>

                <div className="mb-4 d-flex gap-4">
                    <label className="form-label fw-bold p-2">
                        Theatre :
                    </label>
                    <DropdownButton
                        id='dropdown-theatre'
                        title={selectedTheatre ? selectedTheatre.name : " -- Choose a theatre -- "}
                        variant="light"
                        className="text-dark mb-4 border border-dark rounded">
                        {theatres.map((theatre) => (
                            <Dropdown.Item
                                key={theatre.id}
                                onClick={() => { setSelectedTheatre(theatre); fetchSeatTypes(); }}>
                                {theatre.name}
                            </Dropdown.Item>
                        ))}
                    </DropdownButton>
                </div>

                {selectedTheatre && <>
                    <div className="d-flex gap-4">
                        <label className="form-label fw-bold p-2">
                            Seat type :
                        </label>
                        <DropdownButton
                            id='dropdown-seat-type'
                            title={selectedSeatType || ' -- Choose seat type -- '}
                            variant="light"
                            className="border border-dark rounded mb-4 text-dark">

                            {seatTypes.map((seatType) => (
                                <Dropdown.Item
                                    key={seatType}
                                    onClick={() => setSelectedSeatType(seatType)}>
                                    {seatType}
                                </Dropdown.Item>
                            ))}

                        </DropdownButton>
                    </div>


                    {selectedSeatType &&
                        <>
                            <input className="form-control mb-4" type="text" placeholder="Enter the price"
                                value={price} onChange={e => setPrice(parseInt(e.target.value))} required />

                            <input className="form-control mb-4" type="text" placeholder="Enter number of rows"
                                value={rowCount} onChange={e => setRowCount(parseInt(e.target.value))} required />

                            <input className="form-control mb-4" type="text" placeholder="Enter seats per row"
                                value={seatsPerRow} onChange={e => setSeatsPerRow(parseInt(e.target.value))} required />

                            <button className="btn btn-primary d-block mx-auto">Submit</button>
                        </>}
                </>
                }
            </form>

        </Container>
    );
};

export default GenerateSeatLayout;