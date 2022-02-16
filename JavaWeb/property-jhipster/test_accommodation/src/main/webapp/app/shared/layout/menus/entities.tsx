import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/address">
      Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/name">
      Name
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      Customer
    </MenuItem>
    <MenuItem icon="asterisk" to="/property">
      Property
    </MenuItem>
    <MenuItem icon="asterisk" to="/room">
      Room
    </MenuItem>
    <MenuItem icon="asterisk" to="/accommodation">
      Accommodation
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
