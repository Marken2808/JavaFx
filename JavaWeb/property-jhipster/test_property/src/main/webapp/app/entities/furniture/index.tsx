import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Furniture from './furniture';
import FurnitureDetail from './furniture-detail';
import FurnitureUpdate from './furniture-update';
import FurnitureDeleteDialog from './furniture-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FurnitureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FurnitureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FurnitureDetail} />
      <ErrorBoundaryRoute path={match.url} component={Furniture} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FurnitureDeleteDialog} />
  </>
);

export default Routes;
