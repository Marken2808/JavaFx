import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Property from './property';
import PropertyDetail from './property-detail';
import PropertyUpdate from './property-update';
import PropertyDeleteDialog from './property-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PropertyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PropertyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PropertyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Property} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PropertyDeleteDialog} />
  </>
);

export default Routes;
