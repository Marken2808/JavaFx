import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/name">
      <Translate contentKey="global.menu.entities.name" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      <Translate contentKey="global.menu.entities.customer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/property">
      <Translate contentKey="global.menu.entities.property" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/room">
      <Translate contentKey="global.menu.entities.room" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/furniture">
      <Translate contentKey="global.menu.entities.furniture" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/accommodation">
      <Translate contentKey="global.menu.entities.accommodation" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
