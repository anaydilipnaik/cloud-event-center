import { useRef, useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import { alpha } from "@mui/material/styles";
import { Divider, Stack, MenuItem, Avatar, IconButton } from "@mui/material";
import MenuPopover from "../../mui_components/MenuPopover";
import account from "../../_mock/account";
import { AuthConsumer } from "../contexts/Auth/AuthContext";
import { logoutUser } from "../../controllers/authentication";

const MENU_OPTIONS = [
  {
    label: "Home",
    icon: "eva:home-fill",
    linkTo: "/update-user",
  },
  {
    label: "Profile",
    icon: "eva:person-fill",
    linkTo: "/update-user",
  },
];

const AccountPopover = ({ user, processLogout }) => {
  const anchorRef = useRef(null);

  const [open, setOpen] = useState(null);

  const handleOpen = (event) => {
    setOpen(event.currentTarget);
  };

  const handleClose = () => {
    setOpen(null);
  };

  const handleLogout = (e) => {
    e.preventDefault();
    let data = {};
    data.id = user.id;
    logoutUser(data).then(() => {
      processLogout();
    });
  };

  return (
    <>
      <IconButton
        ref={anchorRef}
        onClick={handleOpen}
        sx={{
          p: 0,
          ...(open && {
            "&:before": {
              zIndex: 1,
              content: "''",
              width: "100%",
              height: "100%",
              borderRadius: "50%",
              position: "absolute",
              bgcolor: (theme) => alpha(theme.palette.grey[900], 0.8),
            },
          }),
        }}
      >
        <Avatar src={account.photoURL} alt="photoURL" />
      </IconButton>
      <MenuPopover
        open={Boolean(open)}
        anchorEl={open}
        onClose={handleClose}
        sx={{
          p: 0,
          mt: 1.5,
          ml: 0.75,
          "& .MuiMenuItem-root": {
            typography: "body2",
            borderRadius: 0.75,
          },
        }}
      >
        <Stack sx={{ p: 1 }}>
          {MENU_OPTIONS.map((option) => (
            <MenuItem
              key={option.label}
              to={option.linkTo}
              component={RouterLink}
              onClick={handleClose}
            >
              {option.label}
            </MenuItem>
          ))}
        </Stack>
        <Divider sx={{ borderStyle: "dashed" }} />
        <MenuItem sx={{ m: 1 }} onClick={handleLogout}>
          Logout
        </MenuItem>
      </MenuPopover>
    </>
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user, processLogout }) => (
      <AccountPopover user={user} processLogout={processLogout} {...props} />
    )}
  </AuthConsumer>
);
