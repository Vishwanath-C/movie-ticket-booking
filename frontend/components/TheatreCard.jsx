import EventSeatIcon from '@mui/icons-material/EventSeat';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { Box, Button, Card, CardActions, CardContent, Typography } from "@mui/material";

export default function TheatreCard({ theatre, onDelete, onViewShows }) {
  const hasShows = theatre.shows && theatre.shows.length > 0;

  return (
    <Card
      sx={{
        borderRadius: 4,
        boxShadow: 6,
        transition: "0.3s",
        '&:hover': { boxShadow: 12, transform: "translateY(-4px)" },
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        height: "100%",
        width: 240,
        p: 2,
        mx: "auto",
      }}
    >
      <CardContent>
        <Typography
          variant="h6"
          component="div"
          fontWeight="bold"
          gutterBottom
          sx={{ color: "primary.main" }}
        >
          {theatre.name}
        </Typography>

        {/* Location */}
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <LocationOnIcon sx={{ color: 'text.secondary', mr: 0.5 }} />
          <Typography variant="body2" color="text.secondary">
            {theatre.location}
          </Typography>
        </Box>

        {/* Total Seats */}
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <EventSeatIcon sx={{ color: 'text.secondary', mr: 0.5 }} />
          <Typography variant="body2" color="text.secondary">
            {theatre.seats?.length || 0} Seats
          </Typography>
        </Box>

        {/* Total Shows */}
        {/* <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <MovieIcon sx={{ color: 'text.secondary', mr: 0.5 }} />
          <Typography variant="body2" color="text.secondary">
            {theatre.shows?.length || 0} Shows
          </Typography>
        </Box> */}
      </CardContent>

      <CardActions sx={{ justifyContent: "center", pb: 2, gap: 1 }}>
        <Button
          variant="contained"
          color="primary"
          size="small"
          sx={{ fontWeight: "bold", borderRadius: 3, px: 3 }}
          onClick={() => onViewShows(theatre.id)}
        >
          View Shows
        </Button>

        <Button
          variant="contained"
          color="error"
          size="small"
          sx={{ fontWeight: "bold", borderRadius: 3, px: 3 }}
          onClick={() => onDelete(theatre.id)}
          disabled={hasShows}
          title={hasShows ? "Cannot delete theatre with existing shows" : ""}
        >
          Delete
        </Button>
      </CardActions>
    </Card>
  );
}
