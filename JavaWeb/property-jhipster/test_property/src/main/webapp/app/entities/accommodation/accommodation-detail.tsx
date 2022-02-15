import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './accommodation.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccommodationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const accommodationEntity = useAppSelector(state => state.accommodation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accommodationDetailsHeading">
          <Translate contentKey="testPropertyApp.accommodation.detail.title">Accommodation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accommodationEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="testPropertyApp.accommodation.title">Title</Translate>
            </span>
          </dt>
          <dd>{accommodationEntity.title}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="testPropertyApp.accommodation.type">Type</Translate>
            </span>
          </dt>
          <dd>{accommodationEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="testPropertyApp.accommodation.status">Status</Translate>
            </span>
          </dt>
          <dd>{accommodationEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/accommodation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accommodation/${accommodationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccommodationDetail;
