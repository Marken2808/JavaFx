import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Name from './name';
import NameDetail from './name-detail';
import NameUpdate from './name-update';
import NameDeleteDialog from './name-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NameUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NameUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NameDetail} />
      <ErrorBoundaryRoute path={match.url} component={Name} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NameDeleteDialog} />
  </>
);

export default Routes;
