import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="testPropertyApp.customer.detail.title">Customer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="testPropertyApp.customer.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="testPropertyApp.customer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="birth">
              <Translate contentKey="testPropertyApp.customer.birth">Birth</Translate>
            </span>
          </dt>
          <dd>{customerEntity.birth}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="testPropertyApp.customer.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{customerEntity.gender}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.customer.user">User</Translate>
          </dt>
          <dd>{customerEntity.user ? customerEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.customer.name">Name</Translate>
          </dt>
          <dd>{customerEntity.name ? customerEntity.name.displayName : ''}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.customer.address">Address</Translate>
          </dt>
          <dd>{customerEntity.address ? customerEntity.address.street : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
