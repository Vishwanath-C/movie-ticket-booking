import { useEffect, useState } from "react";
import apiClient from "../src/api";
import ShowSeatLayoutDup from "./ShowSeatLayoutDup";

const AddTheatreAndSeats = () => {
    

    const [theatres, setTheatres] = useState([]);
    const token = localStorage.getItem('token');
    const [selectedTheatre, setSelectedTheatre] = useState(null);
    const [seatTypes, setSeatTypes] = useState([]);
    const [selectedSeatType, setSelectedSeatType] = useState(null);

    const [showAlert, setShowAlert] = useState(false);
    const [alert, setAlert] = useState({ show: false, msg: "", variant: "warning" });

    const [seatDetails, setSeatDetails] = useState([]);
    const [seatDetail, setSeatDetail] = useState(null);

    const [price, setPrice] = useState('');
    // const [numberOfSeats, setNumberOfSeats] = useState('');
    const [rowCount, setRowCount] = useState('');
    const [seatsPerRow, setSeatsPerRow] = useState('');

    const [generateSeats, setGenerateSeats] = useState(false);

    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [seatConfigs, setSeatConfigs] = useState([]);
    const [currentSeatType, setCurrentSeatType] = useState("REGULAR");
    const [currentPrice, setCurrentPrice] = useState(0);
    const [currentRows, setCurrentRows] = useState(0);
    const [currentSeatsPerRow, setCurrentSeatsPerRow] = useState(0);

    useEffect(() => { fetchSeatTypes() }, []);


    // Add a seat configuration to the list
    const addSeatConfig = (e) => {
        e.preventDefault();
        const exists = seatConfigs.some(
            (config) => config.seatType === currentSeatType
        );

        if (exists) {
            setAlert({ show: true, msg: `${currentSeatType} seat type is already added.`, variant: "warning" });
            setTimeout(() => setAlert(a => ({ ...a, show: false })), 3000);
            return;
        }

        setSeatConfigs([
            ...seatConfigs,
            {
                seatType: currentSeatType,
                price: parseFloat(currentPrice),
                rowCount: parseInt(currentRows),
                seatsPerRow: parseInt(currentSeatsPerRow),
            },
        ]);
        // Reset current inputs
        setCurrentSeatType("REGULAR");
        setCurrentPrice(0);
        setCurrentRows(0);
        setCurrentSeatsPerRow(0);
    };

    // Submit theatre with seat configs to backend
    const handleSubmit = async (e) => {
        e.preventDefault();
        const requestBody = {
            name,
            location,
            seatTypeRequests: seatConfigs,
        };

        try {
            const response = await apiClient.post("/theatres/create-theatre", requestBody,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },});
            console.log("Theatre created:", response.data);

            // Reset form
            setName("");
            setLocation("");
            setSeatConfigs([]);

        } catch (error) {
            console.error("Error creating theatre:", error);
        }
    };


    const handleAddSeatDetails = (e) => {
        e.preventDefault();

        setSeatDetail({ price, rowCount, seatsPerRow });


        console.log("Inside add seats", seatsPerRow);



        if (!seatDetail) return;

        console.log("After null");

        setSeatDetails(prev => [...prev, seatDetail]);
        setSeatDetail(null);
        console.log("Seat details : ", seatDetails);
    };

    const fetchSeatTypes = async () => {
        try {
            const response = await apiClient.get('/seats/get-seat-types', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setSeatTypes(response.data);
        } catch (error) {

        }

    };

    return (
        <>
            <div className="container border border-dark rounded lg-light mt-4 p-4 w-50">
                <h3 className="text-center mb-4 fw-bold">Add a Theatre</h3>
                <form className="form-control p-4" onSubmit={handleSubmit}>

                    <label className="form-label fw-bold">Theatre Name</label>
                    <input
                        className="form-control mb-4"
                        type="text"
                        placeholder="Enter theatre name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

                    <label className="form-label fw-bold">Theatre Location</label>
                    <input
                        className="form-control mb-4"
                        type="text"
                        placeholder="Enter location"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        required
                    />



                    <div className="mb-3">
                        <h5 className="mt-4 text-center">Seat Configurations</h5>
                        <label className="form-label fw-bold">Seat Type</label>



                        <select
                            className="form-select text-center"
                            value={currentSeatType}
                            onChange={(e) => setCurrentSeatType(e.target.value)}
                        >
                            <option value="">-- Select Seat Type --</option>
                            {seatTypes.map((type, index) => (
                                <option key={index} value={type}>
                                    {type}
                                </option>
                            ))}
                           
                        </select>

                        <label className="form-label fw-bold">Price</label>
                        <input
                            className="form-control mb-3"
                            type="number"
                            placeholder="Enter price"
                            value={currentPrice}
                            onChange={(e) => setCurrentPrice(e.target.value)}
                            required
                        />

                        <label className="form-label fw-bold">Number of Rows</label>
                        <input
                            className="form-control mb-3"
                            type="number"
                            placeholder="Enter number of rows"
                            value={currentRows}
                            onChange={(e) => setCurrentRows(e.target.value)}
                            required
                        />


                        <label className="form-label fw-bold">Seats Per Row</label>
                        <input
                            className="form-control mb-3"
                            type="number"
                            placeholder="Seats per row"
                            value={currentSeatsPerRow}
                            onChange={(e) => setCurrentSeatsPerRow(e.target.value)}
                            required
                        />
                        <button
                            type="button"
                            className="btn btn-secondary mb-4 d-block mx-auto"
                            onClick={addSeatConfig}
                        >
                            Add Seat Configuration
                        </button>

                    </div>

                    {alert.show && (
                        <div className={`alert alert-${alert.variant} alert-dismissible fade show mt-3`} role="alert">
                            {alert.msg}
                            <button type="button" className="btn-close" onClick={() => setAlert(a => ({ ...a, show: false }))} aria-label="Close"></button>
                        </div>
                    )}

                    {seatConfigs.length > 0 && (
                        <div className="mb-3">
                            <h6 className="fw-bold mb-3">Current Seat Configurations:</h6>
                            <div className="table-responsive">
                                <table className="table table-bordered table-striped align-middle text-center">
                                    <thead className="table-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Seat Type</th>
                                            <th scope="col">Price (₹)</th>
                                            <th scope="col">Rows</th>
                                            <th scope="col">Seats/Row</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {seatConfigs.map((config, index) => (
                                            <tr key={index}>
                                                <td>{index + 1}</td>
                                                <td>{config.seatType}</td>
                                                <td>{config.price}</td>
                                                <td>{config.rows}</td>
                                                <td>{config.seatsPerRow}</td>
                                                <td>
                                                    <button
                                                        className="btn btn-sm btn-danger"
                                                        onClick={() => {
                                                            const updatedConfigs = seatConfigs.filter((_, i) => i !== index);
                                                            setSeatConfigs(updatedConfigs);
                                                        }}
                                                    >
                                                        Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    )}





                    <button type="submit" className="btn btn-primary d-block mx-auto">
                        Add Theatre
                    </button>
                </form>

                <ShowSeatLayoutDup/>

            </div>







        </>
    );
};

export default AddTheatreAndSeats;