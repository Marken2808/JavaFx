import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './property.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PropertyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const propertyEntity = useAppSelector(state => state.property.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="propertyDetailsHeading">
          <Translate contentKey="testPropertyApp.property.detail.title">Property</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="testPropertyApp.property.title">Title</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.title}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="testPropertyApp.property.type">Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="testPropertyApp.property.status">Status</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.status}</dd>
          <dt>
            <span id="isUrgent">
              <Translate contentKey="testPropertyApp.property.isUrgent">Is Urgent</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.isUrgent ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.property.address">Address</Translate>
          </dt>
          <dd>{propertyEntity.address ? propertyEntity.address.street : ''}</dd>
          <dt>
            <Translate contentKey="testPropertyApp.property.accommodation">Accommodation</Translate>
          </dt>
          <dd>{propertyEntity.accommodation ? propertyEntity.accommodation.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/property" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/property/${propertyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PropertyDetail;
