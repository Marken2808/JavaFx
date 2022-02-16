import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './customer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">Customer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="birth">Birth</span>
          </dt>
          <dd>{customerEntity.birth}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{customerEntity.gender}</dd>
          <dt>User</dt>
          <dd>{customerEntity.user ? customerEntity.user.login : ''}</dd>
          <dt>Name</dt>
          <dd>{customerEntity.name ? customerEntity.name.displayName : ''}</dd>
          <dt>Address</dt>
          <dd>{customerEntity.address ? customerEntity.address.street : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
