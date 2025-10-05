import { ExpandLess, ExpandMore } from "@mui/icons-material";
import {
  Box,
  Collapse,
  Divider,
  Drawer,
  List,
  ListItemButton,
  ListItemText,
  useMediaQuery
} from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from "react";
import { matchPath, useLocation, useNavigate } from "react-router-dom";

const drawerWidth = 240;

export default function Sidebar({ setIsLoggedIn, open, onMenuToggle }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [logoutOpen, setLogoutOpen] = useState(false);
  const role = localStorage.getItem("role");

  // Detect mobile using a plain media query string
  const isMobile = useMediaQuery("(max-width:600px)");

  // Menu items
  const adminItems = [
    { id: "sec-home", label: "Home", path: "/app/movies" },
    {
      id: "sec-theatres",
      label: "Theatres",
      children: [
        { id: "theatres-view", label: "View Theatres", path: "/app/theatres" },
        { id: "theatres-add", label: "Add Theatre", path: "/app/theatres/new" },
        { id: "movies-assign", label: "Assign Movie", path: "/app/theatres/assign-movie" },
      ],
    },
    {
      id: "sec-movies",
      label: "Movies",
      children: [
        { id: "movies-add", label: "Add Movie", path: "/app/movies/new" },
        { id: "movies-now", label: "Now Showing", path: "/app/movies" },
        { id: "movies-soon", label: "Coming Soon", path: "/app/movies/upcoming" },
      ],
    },
  ];

  const userItems = [
    { id: "sec-home", label: "Home", path: "/app/movies" },
    {
      id: "sec-user-movies",
      label: "Movies",
      children: [
        { id: "movies-now", label: "Now Showing", path: "/app/movies" },
        { id: "movies-soon", label: "Coming Soon", path: "/app/movies/upcoming" },
      ],
    },
    {
      id: "sec-bookings",
      label: "My Bookings",
      children: [
        { id: "tickets", label: "Tickets", path: "/app/tickets/upcoming" },
        { id: "history", label: "History", path: "/app/tickets/history" },
      ],
    },
  ];

  const items = useMemo(() => (role === "ADMIN" ? adminItems : userItems), [role]);
  const [openMap, setOpenMap] = useState({});

  // Helpers for active route
  const isActiveExact = useCallback(
    (path) => !!path && !!matchPath({ path, end: true }, location.pathname),
    [location.pathname]
  );

  const isSectionActive = useCallback(
    (node) =>
      !!node?.children?.some((c) => (c.path ? isActiveExact(c.path) : false)) ||
      (node.path ? isActiveExact(node.path) : false),
    [isActiveExact]
  );

  useEffect(() => {
    const nextOpen = {};
    const walk = (nodes) => {
      for (const n of nodes) {
        if (n.children && isSectionActive(n)) nextOpen[n.id] = true;
        if (n.children) walk(n.children);
      }
    };
    walk(items);
    setOpenMap((prev) => ({ ...prev, ...nextOpen }));
  }, [items, isSectionActive]);

  const toggleSection = (id) => setOpenMap((m) => ({ ...m, [id]: !m[id] }));

  const handleItemClick = (item) => {
    if (item.action === "logout") setLogoutOpen(true);
    else if (item.path) {
      navigate(item.path);
      if (isMobile && onMenuToggle) onMenuToggle(); // close on mobile
    }
  };

  const renderNode = useCallback(
    (node, depth = 0) => {
      const paddingLeft = 3 + depth * 3;

      if (node.children) {
        const open = !!openMap[node.id];
        const sectionActive = isSectionActive(node);

        return (
          <div key={node.id}>
            <ListItemButton
              onClick={() => toggleSection(node.id)}
              sx={{
                pl: paddingLeft,
                borderRadius: 1,
                mb: 0.5,
                backgroundColor: sectionActive ? "primary.light" : "transparent",
                color: sectionActive ? "white" : "text.primary",
                fontWeight: sectionActive ? 600 : 400,
                "&:hover": { backgroundColor: sectionActive ? "primary.main" : "primary.lighter" },
              }}
            >
              <ListItemText primary={node.label} />
              {open ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <List disablePadding>
                {node.children.map((child) => renderNode(child, depth + 1))}
              </List>
            </Collapse>
          </div>
        );
      }

      const active = isActiveExact(node.path);

      return (
        <ListItemButton
          key={node.id}
          onClick={() => handleItemClick(node)}
          selected={active}
          sx={{
            pl: paddingLeft,
            borderRadius: 1,
            mb: 0.5,
            backgroundColor: active ? "primary.light" : "transparent",
            color: active ? "primary.main" : "text.primary",
            fontWeight: active ? 600 : 400,
            "&:hover": { backgroundColor: "primary.lighter" },
          }}
        >
          <ListItemText primary={node.label} />
        </ListItemButton>
      );
    },
    [openMap, isActiveExact, isSectionActive]
  );

  return (
    <>
      <Drawer
        variant={isMobile ? "temporary" : "permanent"}
        open={isMobile ? open : true}
        onClose={onMenuToggle}
        anchor="left"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
            top: "90px",
            display: "flex",
            flexDirection: "column",
            borderRight: "1px solid #ddd",
            height: `calc(100vh - 90px)`,
          },
        }}
      >
        {/* <Toolbar /> */}
        <Box sx={{ flexGrow: 1, overflowY: "auto", p: 1 }}>
          <List>{items.map((item) => renderNode(item))}</List>
        </Box>
        <Divider />
      </Drawer>
    </>
  );
}
