import PropTypes from "prop-types";
import { forwardRef } from "react";
import { Box, Typography } from "@mui/material";

const Page = forwardRef(({ children, title = "", meta, ...other }, ref) => (
  <>
    <Typography>
      <title>{`${title} | Minimal-UI`}</title>
      {meta}
    </Typography>
    <Box ref={ref} {...other}>
      {children}
    </Box>
  </>
));

Page.propTypes = {
  children: PropTypes.node.isRequired,
  title: PropTypes.string,
  meta: PropTypes.node,
};

export default Page;
