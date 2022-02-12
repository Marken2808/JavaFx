import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './name.reducer';
import { IName } from 'app/shared/model/name.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Name = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const nameList = useAppSelector(state => state.name.entities);
  const loading = useAppSelector(state => state.name.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="name-heading" data-cy="NameHeading">
        <Translate contentKey="testPropertyApp.name.home.title">Names</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="testPropertyApp.name.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="testPropertyApp.name.home.createLabel">Create new Name</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {nameList && nameList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="testPropertyApp.name.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.name.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.name.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.name.middleName">Middle Name</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.name.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="testPropertyApp.name.displayName">Display Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nameList.map((name, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${name.id}`} color="link" size="sm">
                      {name.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`testPropertyApp.Title.${name.title}`} />
                  </td>
                  <td>{name.firstName}</td>
                  <td>{name.middleName}</td>
                  <td>{name.lastName}</td>
                  <td>{name.displayName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${name.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${name.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${name.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="testPropertyApp.name.home.notFound">No Names found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Name;
