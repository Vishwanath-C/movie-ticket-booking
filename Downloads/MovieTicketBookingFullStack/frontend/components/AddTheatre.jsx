import { useState } from "react";
// import axios from "axios";
import apiClient from '../src/api';


const AddTheatre = () => {
    const [name, setName] = useState('');
    const [location, setLocation] = useState('');
    const [goldRowCount, setGoldRowCount] = useState('');
    const [goldColCount, setGoldColCount] = useState('');
    const [normalRowCount, setNormalRowCount] = useState('');
    const [normalColCount, setNormalColCount] = useState('');
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    const handleSubmit = async (e) => {
        e.preventDefault();

        // const parsedGoldRowCount = parseInt(goldRowCount, 10);
        // const parsedGoldColCount = parseInt(goldColCount, 10);
        // const parsedNormalRowCount = parseInt(normalRowCount, 10);
        // const parsedNormalColCount = parseInt(normalColCount, 10);
     
        try {
            const response = await apiClient.post('/theatres/create-theatre', {
                name,
                location,
                // goldRowCount: parsedGoldRowCount,
                // goldColCount: parsedGoldColCount,
                // normalRowCount: parsedNormalRowCount,
                // normalColCount: parsedNormalColCount,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            setName('');
            setLocation('');
 
            console.log(response.data);
        } catch (error) {

        }

    };

    return (
        <div className="container mt-4 border border-dark rounded bg-light p-4 mb-4 w-50">
            <h3 className="text-center mb-4 fw-bold">Add a theatre</h3>
            <form onSubmit={handleSubmit}>
                <input className="form-control mb-4" type="text" placeholder="Enter theatre name"
                    value={name} onChange={e => setName(e.target.value)} required />

                <input className="form-control mb-4" type="text" placeholder="Enter location"
                    value={location} onChange={e => setLocation(e.target.value)} required />

                <button className="btn btn-primary mb-4 d-block mx-auto" type="submit" >Add theatre</button>
            </form>
        </div>
    );
};

export default AddTheatre;