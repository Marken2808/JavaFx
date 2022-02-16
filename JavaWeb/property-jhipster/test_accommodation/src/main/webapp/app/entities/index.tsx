import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Address from './address';
import Name from './name';
import Customer from './customer';
import Property from './property';
import Room from './room';
import Accommodation from './accommodation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}name`} component={Name} />
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}property`} component={Property} />
      <ErrorBoundaryRoute path={`${match.url}room`} component={Room} />
      <ErrorBoundaryRoute path={`${match.url}accommodation`} component={Accommodation} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
