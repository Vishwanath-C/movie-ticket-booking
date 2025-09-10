import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, TextField, Typography, Alert, Paper } from "@mui/material";
import apiClient from "../src/api";

const decodeJWT = (token) => {
  try {
    const payload = token.split('.')[1];
    const decoded = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
    return decoded;
  } catch (err) {
    console.error("Failed to decode JWT:", err);
    return null;
  }
};

const Login = ({ setIsLoggedIn }) => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await apiClient.post('/auth/login', { email, password });
      const token = res.data.accessToken;

      if (!token) throw new Error("No token received from backend.");

      localStorage.setItem("token", token);

      const decodedJWT = decodeJWT(token);
      if (!decodedJWT) throw new Error("Failed to decode token.");

      localStorage.setItem("role", decodedJWT.role);
      console.log("Role ",decodedJWT.role);

      setIsLoggedIn(true);
      navigate('/app/movies');
    } catch (err) {
      console.error("Login failed:", err);
      setError(true);
    }
  };

  return (
    <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", minHeight: "100vh", bgcolor: "#f0f2f5", p: 2 }}>
      <Paper sx={{ p: 4, width: 400, maxWidth: "90%", borderRadius: 3, boxShadow: 3 }}>
        <Typography variant="h5" align="center" fontWeight="bold" gutterBottom>Login</Typography>

        <form onSubmit={handleSubmit}>
          <TextField
            label="Email"
            type="email"
            fullWidth
            margin="normal"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <TextField
            label="Password"
            type="password"
            fullWidth
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2, fontWeight: "bold" }}>
            Login
          </Button>

          {error && (
            <Alert
              severity="error"
              sx={{ mt: 3 }}
              action={
                <Button color="inherit" size="small" onClick={() => { setError(false); setEmail(''); setPassword(''); }}>
                  Retry
                </Button>
              }
            >
              Credentials are incorrect
            </Alert>
          )}
        </form>
      </Paper>
    </Box>
  );
};

export default Login;
