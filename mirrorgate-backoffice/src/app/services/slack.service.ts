/*
 * Copyright 2017 Banco Bilbao Vizcaya Argentaria, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Injectable } from '@angular/core';

import { HttpClient, HttpParams } from '@angular/common/http';

import { ConfigService } from './config.service';
import { Dashboard } from '../model/dashboard';

@Injectable()
export class SlackService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  signSlack(team:string, clientId:string, clientSecret:string): Promise<any> {
    var dummy: HTMLAnchorElement = document.createElement('a');
    dummy.href = 'utils/slack/code-capturer';

    return new Promise((resolve, reject) =>  {
      var source = window.open(
        `https://slack.com/oauth/authorize?client_id=${clientId}&scope=client&team=${team}`
      );

      var receiver = (msg: MessageEvent) =>  {
        window.removeEventListener("message", receiver, false);
        if(msg.source == source && msg.origin == document.location.origin) {
          this.generateToken(msg.data, team, clientId, clientSecret).subscribe(
            token => resolve(token),
            error => reject(error)
          );
        } else {
          reject('Slack Token could not be generated');
        }
      };
      window.addEventListener("message", receiver, false);
    });
  }

  private generateToken(code:string, team:string, clientId:string, clientSecret:string) {
    let params: HttpParams = new HttpParams()
      .set('code', code)
      .set('team', team)
      .set('clientId', clientId)
      .set('clientSecret', clientSecret);

    return this.http.get(`${this.configService.getConfig('MIRRORGATE_API_URL')}/slack/token-generator`, {
      params: params,
      responseType: 'text'
    });
  }

  getChannels(dashboard:Dashboard) {
    let params: HttpParams = new HttpParams()
      .set('dashboard', dashboard.name)
      .set('token', dashboard.slackToken);

    return this.http.get(`${this.configService.getConfig('MIRRORGATE_API_URL')}/slack/channels`, {
      params: params
    });
  }

}
