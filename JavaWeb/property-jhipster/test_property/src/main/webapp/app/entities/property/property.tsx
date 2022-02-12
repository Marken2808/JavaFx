import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './property.reducer';
import { IProperty } from 'app/shared/model/property.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Property = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const propertyList = useAppSelector(state => state.property.entities);
  const loading = useAppSelector(state => state.property.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="property-heading" data-cy="PropertyHeading">
        <Translate contentKey="testPropertyApp.property.home.title">Properties</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testPropertyApp.property.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testPropertyApp.property.home.createLabel">Create new Property</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {propertyList && propertyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="testPropertyApp.property.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.image">Image</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.acreage">Acreage</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.property.address">Address</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {propertyList.map((property, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${property.id}`} color="link" size="sm">
                      {property.id}
                    </Button>
                  </td>
                  <td>{property.title}</td>
                  <td>
                    {property.image ? (
                      <div>
                        {property.imageContentType ? (
                          <a onClick={openFile(property.imageContentType, property.image)}>
                            <img src={`data:${property.imageContentType};base64,${property.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {property.imageContentType}, {byteSize(property.image)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`testPropertyApp.Status.${property.status}`} />
                  </td>
                  <td>
                    <Translate contentKey={`testPropertyApp.Type.${property.type}`} />
                  </td>
                  <td>{property.acreage}</td>
                  <td>{property.address ? <Link to={`address/${property.address.id}`}>{property.address.street}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${property.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${property.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${property.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="testPropertyApp.property.home.notFound">No Properties found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Property;
