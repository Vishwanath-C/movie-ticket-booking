import EventSeatIcon from '@mui/icons-material/EventSeat';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { Box, Button, Card, CardActions, CardContent, Typography, Paper } from "@mui/material";

// Modern row with subtle background
function InfoRow({ icon: Icon, text }) {
  return (
    <Paper
      elevation={0}
      sx={{
        display: 'flex',
        alignItems: 'center',
        p: 1,
        mb: 1,
        borderRadius: 2,
        bgcolor: 'grey.100',
      }}
    >
      <Icon sx={{ color: 'primary.main', mr: 1 }} />
      <Typography variant="body2" color="text.secondary" fontWeight={"bold"}>
        {text}
      </Typography>
    </Paper>
  );
}

export default function TheatreCard({ theatre, onDelete, onViewShows }) {
  const hasShows = theatre.shows?.length > 0;

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
        width: 260,
        p: 2,
        mx: "auto",
        bgcolor: 'background.paper',
      }}
    >
      <CardContent sx={{ pb: 1 }}>
        <Typography
          variant="h6"
          component="div"
          fontWeight="bold"
          gutterBottom
          sx={{ color: "primary.main" }}
        >
          {theatre.name ?? "Unknown Theatre"}
        </Typography>

        <InfoRow icon={LocationOnIcon} text={theatre.location ?? "Unknown Location"} />
        <InfoRow icon={EventSeatIcon} text={`${theatre.seats?.length || 0} Seats`} />
        {/* Optional shows row */}
        {/* <InfoRow icon={MovieIcon} text={`${theatre.shows?.length || 0} Shows`} /> */}
      </CardContent>

      <CardActions sx={{ justifyContent: "center", pb: 2, gap: 1 }}>
        <Button
          variant="contained"
          color="primary"
          size="small"
          sx={{ fontWeight: "bold", borderRadius: 3, px: 3 }}
          onClick={() => onViewShows(theatre.id)}
          aria-label={`View shows for ${theatre.name}`}
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
          aria-label={`Delete ${theatre.name}`}
        >
          Delete
        </Button>
      </CardActions>
    </Card>
  );
}
